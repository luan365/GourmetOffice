// TelaLogin.jsx
import React from 'react';
import './TelaLogin.css';

function LoginPage({ backgroundImage, backgroundColor }) {
  const containerStyle = {
    backgroundImage: backgroundImage ? `url(${backgroundImage})` : 'none',
    backgroundColor: backgroundColor ? backgroundColor : '#f5f5f5',
    backgroundSize: 'cover',          // Isso garante que a imagem cubra a tela toda
    backgroundPosition: 'center',     // A imagem ficará centralizada
  };

  return (
    <div className="container" style={containerStyle}>
      <div className="image">Imagem</div>
      <input type="email" placeholder="email" className="input" />
      <input type="password" placeholder="Senha" className="input" />
      <button className="button">Recuperar senha</button>
    </div>
  );
}

export default LoginPage;
