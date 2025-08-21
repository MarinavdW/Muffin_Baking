import React, { useState, useEffect } from 'react';
import axios from 'axios';
import MuffinBoard from './components/MuffinBoard';
import AuthSection from './components/AuthSection';
import AddMuffinModal from './components/AddMuffinModal';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [lists, setLists] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    checkAuthStatus();
  }, []);

  useEffect(() => {
    if (isAuthenticated) {
      loadBoardData();
    }
  }, [isAuthenticated]);

  const checkAuthStatus = async () => {
    try {
      const response = await axios.get('/api/auth/status');
      setIsAuthenticated(response.data.authenticated);
    } catch (err) {
      console.error('Auth check failed:', err);
      setIsAuthenticated(false);
    } finally {
      setIsLoading(false);
    }
  };

  const loadBoardData = async () => {
    try {
      setIsLoading(true);
      const response = await axios.get('/api/board/lists');
      setLists(response.data);
      setError(null);
    } catch (err) {
      console.error('Failed to load board data:', err);
      setError('Failed to load board data. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddMuffin = async (muffinName, listId) => {
    try {
      const response = await axios.post('/api/board/cards', {
        name: muffinName,
        listId: listId
      });
      
      if (response.data) {
        await loadBoardData();
        setShowAddModal(false);
      }
    } catch (err) {
      console.error('Failed to add muffin:', err);
      setError('Failed to add muffin. Please try again.');
    }
  };

  if (isLoading) {
    return (
      <div className="app">
        <div className="loading">
          <h2>🧁 Loading Muffin Board...</h2>
        </div>
      </div>
    );
  }

  return (
    <div className="app">
      <div className="app-header">
        <h1>🧁 Muffin Board</h1>
        <p>Zoho Connect Integration for B&amp; Digital Transformation</p>
        {isAuthenticated && (
          <button 
            className="add-card-button" 
            onClick={() => setShowAddModal(true)}
            style={{ maxWidth: '200px', margin: '10px auto' }}
          >
            + Add New Muffin
          </button>
        )}
      </div>

      {error && (
        <div className="error">
          {error}
        </div>
      )}

      {!isAuthenticated ? (
        <AuthSection onAuthSuccess={() => {
          setIsAuthenticated(true);
          loadBoardData();
        }} />
      ) : (
        <MuffinBoard lists={lists} onRefresh={loadBoardData} />
      )}

      {showAddModal && (
        <AddMuffinModal
          lists={lists}
          onSave={handleAddMuffin}
          onClose={() => setShowAddModal(false)}
        />
      )}
    </div>
  );
}

export default App;