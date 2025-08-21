import React from 'react';
import MuffinList from './MuffinList';

function MuffinBoard({ lists, onRefresh }) {
  if (!lists || lists.length === 0) {
    return (
      <div style={{ textAlign: 'center', padding: '40px' }}>
        <h3>No lists found</h3>
        <p>Make sure your Zoho Connect board has "To Bake" and "Already Baked" lists.</p>
        <button className="btn-primary" onClick={onRefresh}>
          Refresh Board
        </button>
      </div>
    );
  }

  return (
    <div className="board">
      {lists.map(list => (
        <MuffinList 
          key={list.id} 
          list={list}
        />
      ))}
    </div>
  );
}

export default MuffinBoard;