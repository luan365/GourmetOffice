// main.jsx
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import TelaLogin from './TelaLogin.jsx';
import './main.css';

// Se quiser usar uma imagem, pode definir aqui
const backgroundImage = 'path/to/your/image.png';
//const backgroundColor = cor


//se quiser cor no lugar de imagem, so colocar "<App backgroundColor={backgroundColor} />"
createRoot(document.getElementById('root')).render(
  <StrictMode>

    <TelaLogin backgroundImage={backgroundImage} />
  </StrictMode>,
);
