$headers = @{
    "Content-Type" = "application/json"
}

$body = @{
    eventType = "task.moved"
    task = @{
        title = "Test Chocolate Muffin"
    }
    fromSection = @{
        name = "To Bake"
    }
    toSection = @{
        name = "Already Baked"
    }
} | ConvertTo-Json -Depth 3

Write-Host "Testing webhook with payload:"
Write-Host $body

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/webhook/zoho-connect" -Method POST -Headers $headers -Body $body
    Write-Host "Webhook processed successfully!" -ForegroundColor Green
    Write-Host ($response | ConvertTo-Json)
} catch {
    Write-Host "Error calling webhook: $($_.Exception.Message)" -ForegroundColor Red
}
