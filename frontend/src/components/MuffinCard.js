import React from 'react';

function MuffinCard({ card }) {
  return (
    <div className="card">
      <h4>🧁 {card.name}</h4>
    </div>
  );
}

export default MuffinCard;