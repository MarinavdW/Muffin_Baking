$headers = @{
    "Content-Type" = "application/json"
}

$body = @{
    name = "Test Strawberry Muffin"
    listId = "705113000000002054"
} | ConvertTo-Json

Write-Host "Testing muffin creation with payload:"
Write-Host $body

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/board/cards" -Method POST -Headers $headers -Body $body
    Write-Host "Created muffin successfully!" -ForegroundColor Green
    Write-Host ($response | ConvertTo-Json)
} catch {
    Write-Host "Error creating muffin: $($_.Exception.Message)" -ForegroundColor Red
}