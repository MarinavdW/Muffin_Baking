#!/bin/bash

echo "🧪 Testing Muffin Board OAuth Authentication Flow"
echo "================================================="
echo ""

echo "1. Testing backend server availability..."
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" http://localhost:8080/api/auth/status || echo "❌ Backend not accessible from WSL"

echo ""
echo "2. Testing OAuth authorization endpoint (should redirect to Zoho)..."
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" http://localhost:8080/oauth2/authorization/zoho || echo "❌ OAuth endpoint not accessible from WSL"

echo ""
echo "📝 To test the OAuth flow manually:"
echo "   1. Open your browser and go to: http://localhost:8080/oauth2/authorization/zoho"
echo "   2. You should be redirected to Zoho for authentication"
echo "   3. After successful login, you should be redirected to: http://localhost:3000"
echo "   4. Check auth status: http://localhost:8080/api/auth/status"
echo ""
echo "📝 Updated Configuration:"
echo "   - Backend Port: 8080"
echo "   - OAuth Scopes: zohopulse.feedList.READ,CREATE,UPDATE + aaaserver.profile.READ"
echo "   - Redirect URI: http://localhost:8080/login/oauth2/code/zoho"
echo ""
echo "🎯 Next Steps:"
echo "   1. Test OAuth flow in browser"
echo "   2. Verify board data loading after authentication"  
echo "   3. Test muffin card creation functionality"