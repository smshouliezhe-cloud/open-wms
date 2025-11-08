package com.yiruantong.common.oss.core;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.encrypt.utils.SecurityUtil;
import com.yiruantong.common.oss.constant.OssConstant;
import com.yiruantong.common.oss.entity.UploadResult;
import com.yiruantong.common.oss.enumd.AccessPolicyType;
import com.yiruantong.common.oss.enumd.PolicyType;
import com.yiruantong.common.oss.exception.OssException;
import com.yiruantong.common.oss.properties.OssProperties;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * S3 存储协议 所有兼容S3协议的云厂商均支持
 * 阿里云 腾讯云 七牛云 minio
 *
 * @author YiRuanTong
 */
public class OssClient {

  private final String configKey;

  private final OssProperties properties;

  private final AmazonS3 client;

  public OssClient(String configKey, OssProperties ossProperties) {
    this.configKey = configKey;
    this.properties = ossProperties;
    try {
      AwsClientBuilder.EndpointConfiguration endpointConfig =
        new AwsClientBuilder.EndpointConfiguration(properties.getEndpoint(), properties.getRegion());

      AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
      AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
      ClientConfiguration clientConfig = new ClientConfiguration();
      if (OssConstant.IS_HTTPS.equals(properties.getIsHttps())) {
        clientConfig.setProtocol(Protocol.HTTPS);
      } else {
        clientConfig.setProtocol(Protocol.HTTP);
      }
      AmazonS3ClientBuilder build = AmazonS3Client.builder()
        .withEndpointConfiguration(endpointConfig)
        .withClientConfiguration(clientConfig)
        .withCredentials(credentialsProvider)
        .disableChunkedEncoding();
      if (!StringUtils.containsAny(properties.getEndpoint(), OssConstant.CLOUD_SERVICE)) {
        // minio 使用https限制使用域名访问 需要此配置 站点填域名
        build.enablePathStyleAccess();
      }
      this.client = build.build();

      createBucket();
    } catch (Exception e) {
      if (e instanceof OssException) {
        throw e;
      }
      throw new OssException("配置错误! 请检查系统配置:[" + e.getMessage() + "]");
    }
  }

  private static String getPolicy(String bucketName, PolicyType policyType) {
    StringBuilder builder = new StringBuilder();
    builder.append("{\n\"Statement\": [\n{\n\"Action\": [\n");
    builder.append(switch (policyType) {
      case WRITE -> "\"s3:GetBucketLocation\",\n\"s3:ListBucketMultipartUploads\"\n";
      case READ_WRITE -> "\"s3:GetBucketLocation\",\n\"s3:ListBucket\",\n\"s3:ListBucketMultipartUploads\"\n";
      default -> "\"s3:GetBucketLocation\"\n";
    });
    builder.append("],\n\"Effect\": \"Allow\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
    builder.append(bucketName);
    builder.append("\"\n},\n");
    if (policyType == PolicyType.READ) {
      builder.append("{\n\"Action\": [\n\"s3:ListBucket\"\n],\n\"Effect\": \"Deny\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
      builder.append(bucketName);
      builder.append("\"\n},\n");
    }
    builder.append("{\n\"Action\": ");
    builder.append(switch (policyType) {
      case WRITE ->
        "[\n\"s3:AbortMultipartUpload\",\n\"s3:DeleteObject\",\n\"s3:ListMultipartUploadParts\",\n\"s3:PutObject\"\n],\n";
      case READ_WRITE ->
        "[\n\"s3:AbortMultipartUpload\",\n\"s3:DeleteObject\",\n\"s3:GetObject\",\n\"s3:ListMultipartUploadParts\",\n\"s3:PutObject\"\n],\n";
      default -> "\"s3:GetObject\",\n";
    });
    builder.append("\"Effect\": \"Allow\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
    builder.append(bucketName);
    builder.append("/*\"\n}\n],\n\"Version\": \"2012-10-17\"\n}\n");
    return builder.toString();
  }

  public void createBucket() {
    try {
      String bucketName = properties.getBucketName();
      if (client.doesBucketExistV2(bucketName)) {
        return;
      }
      CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
      AccessPolicyType accessPolicy = getAccessPolicy();
      createBucketRequest.setCannedAcl(accessPolicy.getAcl());
      client.createBucket(createBucketRequest);
      client.setBucketPolicy(bucketName, getPolicy(bucketName, accessPolicy.getPolicyType()));
    } catch (Exception e) {
      throw new OssException("创建Bucket失败, 请核对配置信息:[" + e.getMessage() + "]");
    }
  }

  public UploadResult upload(byte[] data, String path, String contentType) {
    return upload(new ByteArrayInputStream(data), path, contentType);
  }

  public UploadResult upload(InputStream inputStream, String path, String contentType) {
    if (!(inputStream instanceof ByteArrayInputStream)) {
      inputStream = new ByteArrayInputStream(IoUtil.readBytes(inputStream));
    }
    long size;
    try {
      size = inputStream.available();
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentType);
      metadata.setContentLength(size);
      PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), path, inputStream, metadata);
      // 设置上传对象的 Acl 为公共读
      putObjectRequest.setCannedAcl(getAccessPolicy().getAcl());
      client.putObject(putObjectRequest);
    } catch (Exception e) {
      throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
    }
    return UploadResult.builder().url(getUrl() + "/" + path).filename(path)
      .fileSize(Convert.toBigDecimal(size)).build();
  }

  public UploadResult upload(File file, String path) {
    try {
      PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), path, file);
      // 设置上传对象的 Acl 为公共读
      putObjectRequest.setCannedAcl(getAccessPolicy().getAcl());
      client.putObject(putObjectRequest);
    } catch (Exception e) {
      throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
    }
    return UploadResult.builder().url(getUrl() + "/" + path).filename(path)
      .fileSize(Convert.toBigDecimal(file.length())).build();
  }

  /**
   * 生成缩略图
   *
   * @param data
   * @param targetWidth
   * @param targetHeight
   * @throws IOException
   */
  public void createThumbnail(byte[] data, String suffix, String path, String contentType, int targetWidth, int targetHeight) throws IOException {
    if (!java.util.List.of(".png", ".jpg", ".jpeg", ".bmp", ".gif", ".webp").contains(suffix)) {
      return;
    }

    // 读取原始图像
    ByteArrayInputStream bis = new ByteArrayInputStream(data);
    BufferedImage originalImage = ImageIO.read(bis);
    if (originalImage == null) {
      throw new IllegalArgumentException("无法读取提供的文件作为图像");
    }

    // 创建缩略图
    BufferedImage thumbnail = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = thumbnail.createGraphics();
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    graphics2D.dispose();

    // 上传缩略图
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    String formatName = suffix.replace(".", "");
    ImageIO.write(thumbnail, formatName, baos);

    String[] paths = StringUtils.split(path, "/");
    String fileName = paths[paths.length - 1];
    path = StringUtils.replace(path, fileName, "thumb/" + fileName); // 缩略图路径
    upload(baos.toByteArray(), path, contentType);
  }

  public void delete(String path) {
    path = path.replace(getUrl() + "/", "");
    try {
      client.deleteObject(properties.getBucketName(), path);
    } catch (Exception e) {
      throw new OssException("删除文件失败，请检查配置信息:[" + e.getMessage() + "]");
    }
  }

  public UploadResult uploadSuffix(byte[] data, String suffix, String contentType) {
    String md5Key = getMd5Key(data);
    String path = getPath(md5Key, properties.getPrefix(), suffix);
    UploadResult uploadResult = upload(data, path, contentType);
    uploadResult.setMd5Key(md5Key);

    // 生成缩略图
    try {
      createThumbnail(data, suffix, path, contentType, 100, 100);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return uploadResult;
  }

  public UploadResult uploadSuffix(String md5Key, File file, String suffix) throws IOException {
    String prefix = properties.getPrefix();
    String path = getPath(md5Key, prefix, suffix);

    UploadResult uploadResult = upload(file, path);
    uploadResult.setMd5Key(md5Key);
    return uploadResult;
  }

  public UploadResult uploadSuffix(InputStream inputStream, String suffix, String contentType) throws IOException {
    String md5Key = getMd5Key(inputStream);
    return upload(inputStream, getPath(md5Key, properties.getPrefix(), suffix), contentType);
  }

  public String getMd5Key(File file) {
    try {
      FileInputStream inputStream = new FileInputStream(file);
      return SecurityUtil.MD5(inputStream.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getMd5Key(byte[] data) {
    return SecurityUtil.MD5(data); // IdUtil.fastSimpleUUID();
  }

  public String getMd5Key(InputStream inputStream) {
    try {
      return SecurityUtil.MD5(inputStream.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取文件元数据
   *
   * @param path 完整文件路径
   */
  public ObjectMetadata getObjectMetadata(String path) {
    path = path.replace(getUrl() + "/", "");
    S3Object object = client.getObject(properties.getBucketName(), path);
    return object.getObjectMetadata();
  }

  public InputStream getObjectContent(String path) {
    path = path.replace(getUrl() + "/", "");
    S3Object object = client.getObject(properties.getBucketName(), path);
    return object.getObjectContent();
  }

  public String getUrl() {
    String domain = properties.getDomain();
    String endpoint = properties.getEndpoint();
    String header = OssConstant.IS_HTTPS.equals(properties.getIsHttps()) ? "https://" : "http://";
    // 云服务商直接返回
    if (StringUtils.containsAny(endpoint, OssConstant.CLOUD_SERVICE)) {
      if (StringUtils.isNotBlank(domain)) {
        return header + domain;
      }
      return header + properties.getBucketName() + "." + endpoint;
    }
    // minio 单独处理
    if (StringUtils.isNotBlank(domain)) {
      return header + domain + "/" + properties.getBucketName();
    }
    return header + endpoint + "/" + properties.getBucketName();
  }

  public String getPath(String md5Key, String prefix, String suffix) {
    // 文件路径
    String path = DateUtils.weekPath() + "/" + md5Key;
    if (StringUtils.isNotBlank(prefix)) {
      path = prefix + "/" + path;
    }
    return path + suffix;
  }

  public String getConfigKey() {
    return configKey;
  }

  /**
   * 获取私有URL链接
   *
   * @param objectKey 对象KEY
   * @param second    授权时间
   */
  public String getPrivateUrl(String objectKey, Integer second) {
    GeneratePresignedUrlRequest generatePresignedUrlRequest =
      new GeneratePresignedUrlRequest(properties.getBucketName(), objectKey)
        .withMethod(HttpMethod.GET)
        .withExpiration(new Date(System.currentTimeMillis() + 1000L * second));
    URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
    return url.toString();
  }

  /**
   * 检查配置是否相同
   */
  public boolean checkPropertiesSame(OssProperties properties) {
    return this.properties.equals(properties);
  }

  /**
   * 获取当前桶权限类型
   *
   * @return 当前桶权限类型code
   */
  public AccessPolicyType getAccessPolicy() {
    return AccessPolicyType.getByType(properties.getAccessPolicy());
  }

}
