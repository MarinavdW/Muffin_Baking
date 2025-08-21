#!/bin/bash

echo "🧪 Testing Muffin Board Endpoints"
echo "================================="

BASE_URL="http://localhost:8080"

echo ""
echo "1. Testing webhook test endpoint..."
curl -s -X GET "$BASE_URL/api/webhook/test" | jq '.' 2>/dev/null || curl -s -X GET "$BASE_URL/api/webhook/test"

echo ""
echo ""
echo "2. Testing auth status endpoint..."
curl -s -X GET "$BASE_URL/api/auth/status" | jq '.' 2>/dev/null || curl -s -X GET "$BASE_URL/api/auth/status"

echo ""
echo ""
echo "3. Testing auth login redirect (will show redirect location)..."
curl -s -I "$BASE_URL/api/auth/login" | grep -i location || echo "Endpoint not accessible or backend not running"

echo ""
echo ""
echo "🚀 To run the application:"
echo "Backend: cd backend && mvn spring-boot:run"
echo "Frontend: cd frontend && npm start"
echo ""
echo "📝 Note: Ensure Java 17+ and Maven are installed for backend"
echo "📝 Note: Authentication required for board endpoints"