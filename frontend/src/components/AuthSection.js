import React from 'react';

function AuthSection({ onAuthSuccess }) {
  const handleLogin = () => {
    // Open OAuth in same window to maintain session
    window.location.href = 'http://localhost:8080/oauth2/authorization/zoho';
  };

  return (
    <div className="auth-section">
      <h2>🔐 Authentication Required</h2>
      <p>Please authenticate with Zoho Connect to access your Muffin Baking board.</p>
      <button className="login-button" onClick={handleLogin}>
        Connect to Zoho
      </button>
      <p style={{ marginTop: '20px', fontSize: '0.9rem', color: '#7f8c8d' }}>
        You'll be redirected to Zoho's secure authentication page.
      </p>
    </div>
  );
}

export default AuthSection;