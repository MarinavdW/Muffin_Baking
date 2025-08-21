import React from 'react';
import MuffinCard from './MuffinCard';

function MuffinList({ list }) {
  return (
    <div className="list">
      <div className="list-header">
        <h3 className="list-title">{list.name}</h3>
        <span className="card-count">{list.cards ? list.cards.length : 0}</span>
      </div>
      <div className="cards">
        {list.cards && list.cards.length > 0 ? (
          list.cards.map(card => (
            <MuffinCard key={card.id} card={card} />
          ))
        ) : (
          <div style={{ 
            textAlign: 'center', 
            color: '#7f8c8d', 
            padding: '20px',
            fontStyle: 'italic' 
          }}>
            {list.name === 'To Bake' ? '🥧 No muffins to bake yet' : '🧁 No baked muffins yet'}
          </div>
        )}
      </div>
    </div>
  );
}

export default MuffinList;