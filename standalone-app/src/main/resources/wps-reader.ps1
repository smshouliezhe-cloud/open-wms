$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)

$app = $null
$progIds = @('KET.Application', 'Ket.Application', 'ET.Application', 'Et.Application')
foreach ($progId in $progIds) {
    try {
        $app = [Runtime.InteropServices.Marshal]::GetActiveObject($progId)
        if ($null -ne $app) { break }
    } catch { }
}

if ($null -eq $app) {
    throw '未找到正在运行的 WPS 表格。请先打开 WPS 表格并选中需要导入的工作表。'
}

$book = $app.ActiveWorkbook
$sheet = $app.ActiveSheet
if ($null -eq $book -or $null -eq $sheet) {
    throw 'WPS 已打开，但没有活动工作簿或工作表。'
}

$used = $sheet.UsedRange
$firstRow = [int]$used.Row
$firstColumn = [int]$used.Column
$rowCount = [Math]::Min([int]$used.Rows.Count, 5000)
$columnCount = [Math]::Min([int]$used.Columns.Count, 80)

$rows = New-Object System.Collections.ArrayList
for ($r = 0; $r -lt $rowCount; $r++) {
    $line = New-Object System.Collections.ArrayList
    for ($c = 0; $c -lt $columnCount; $c++) {
        $cell = $sheet.Cells.Item($firstRow + $r, $firstColumn + $c)
        $text = ''
        if ($null -ne $cell.Text) { $text = [string]$cell.Text }
        [void]$line.Add($text)
        [void][Runtime.InteropServices.Marshal]::ReleaseComObject($cell)
    }
    [void]$rows.Add($line.ToArray())
}

$result = [ordered]@{
    workbook = [string]$book.Name
    sheet = [string]$sheet.Name
    firstRow = $firstRow
    firstColumn = $firstColumn
    rows = $rows.ToArray()
}

$result | ConvertTo-Json -Depth 8 -Compress

[void][Runtime.InteropServices.Marshal]::ReleaseComObject($used)
[void][Runtime.InteropServices.Marshal]::ReleaseComObject($sheet)
[void][Runtime.InteropServices.Marshal]::ReleaseComObject($book)
[void][Runtime.InteropServices.Marshal]::ReleaseComObject($app)
