import React, { useState } from 'react';

function AddMuffinModal({ lists, onSave, onClose }) {
  const [muffinName, setMuffinName] = useState('');
  const [selectedListId, setSelectedListId] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  React.useEffect(() => {
    const toBakeList = lists.find(list => list.name === 'To Bake');
    if (toBakeList) {
      setSelectedListId(toBakeList.id);
    }
  }, [lists]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!muffinName.trim()) return;

    setIsSubmitting(true);
    try {
      await onSave(muffinName.trim(), selectedListId);
    } finally {
      setIsSubmitting(false);
    }
  };

  const muffinSuggestions = [
    'Blueberry Muffin',
    'Chocolate Chip Muffin',
    'Banana Nut Muffin',
    'Lemon Poppy Seed Muffin',
    'Cranberry Orange Muffin',
    'Double Chocolate Muffin',
    'Strawberry Muffin',
    'Apple Cinnamon Muffin'
  ];

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()}>
        <h2>🧁 Add New Muffin</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="muffinName">Muffin Name</label>
            <input
              type="text"
              id="muffinName"
              value={muffinName}
              onChange={(e) => setMuffinName(e.target.value)}
              placeholder="Enter delicious muffin name..."
              required
              autoFocus
            />
          </div>

          <div className="form-group">
            <label>Quick suggestions:</label>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px', marginTop: '8px' }}>
              {muffinSuggestions.map(suggestion => (
                <button
                  key={suggestion}
                  type="button"
                  onClick={() => setMuffinName(suggestion)}
                  style={{
                    background: '#ecf0f1',
                    border: 'none',
                    padding: '4px 8px',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    fontSize: '0.9rem'
                  }}
                >
                  {suggestion}
                </button>
              ))}
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="listId">Add to List</label>
            <select
              id="listId"
              value={selectedListId}
              onChange={(e) => setSelectedListId(e.target.value)}
              style={{
                width: '100%',
                padding: '10px',
                border: '2px solid #ecf0f1',
                borderRadius: '6px',
                fontSize: '1rem'
              }}
            >
              {lists.map(list => (
                <option key={list.id} value={list.id}>
                  {list.name}
                </option>
              ))}
            </select>
          </div>

          <div className="modal-buttons">
            <button type="button" className="btn-secondary" onClick={onClose}>
              Cancel
            </button>
            <button 
              type="submit" 
              className="btn-primary"
              disabled={isSubmitting || !muffinName.trim()}
            >
              {isSubmitting ? 'Adding...' : 'Add Muffin'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default AddMuffinModal;