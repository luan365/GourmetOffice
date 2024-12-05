import { Link } from "react-router-dom";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function RegisterPage() {
  const [email, setEmail] = useState("");
  const [nome, setNome] = useState("");
  const [senha, setSenha] = useState("");
  const [senhaConfirma, setSenhaConfirma] = useState("");
  const [endereco, setEndereco] = useState("");
  const [cnpj, setCnpj] = useState("");
  const [telefone, setTelefone] = useState("");
  const [descricao, setDescricao] = useState("");
  const [numEstados, setNumEstados] = useState("");
  const [estadosSelecionados, setEstadosSelecionados] = useState([]);
  const tipo = "cozinha";
  const notas = [];

  const navigate = useNavigate();

  const estados = [
    "AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", 
    "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"
  ];

  

  async function registerUser(ev) {
    ev.preventDefault();

    // Validações


    if (senha !== senhaConfirma) {
      alert("Senhas não correspondem");
      return;
    }

    if (senha.length < 8) {
      alert("Senha precisa ter no mínimo 8 dígitos");
      return;
    }

    if (cnpj.length !== 14) {
      alert("CNPJ inválido");
      return;
    }

    if (nome.length === 0) {
      alert("Preencha seu nome");
      return;
    }

    if (email.length === 0) {
      alert("Preencha seu email");
      return;
    }

    if (telefone.length === 0) {
      alert("Preencha seu telefone");
      return;
    }

    if (endereco.length === 0) {
      alert("Preencha seu endereço");
      return;
    }

    ev.preventDefault();
    try {
      let success = false;

     
        await axios.put('http://localhost:8080/insertCozinha', {
          nome,
          senha,
          email,
          endereco,
          cnpj,
          telefone,
          descricao,
          tipo,
          notas
        }).then(response => {
          success = true;
          alert('Sua cozinha foi cadastrada com sucesso!');
        }).catch(error => {
          console.error('Erro ao cadastrar cozinha:', error.message);
          alert('Erro ao cadastrar Cozinha:'+ error.response.data);
          
        });
      


      if (success) {
        setTimeout(() => {
          navigate("/login");
        }, 300);
      }
    } catch (e) {
      alert('Erro no registro: ' + e);
    }
  }

  const handleNumEstadosChange = (e) => {
    const quantidade = parseInt(e.target.value);
    setNumEstados(quantidade);
    
    // Cria uma lista de dropdowns de estados
    const estadosSelecionadosArray = [];
    for (let i = 0; i < quantidade; i++) {
      estadosSelecionadosArray.push("");
    }
    setEstadosSelecionados(estadosSelecionadosArray);
  };

  // Função para atualizar o estado selecionado em um dos dropdowns
  const handleEstadoChange = (index, value) => {
    const updatedEstados = [...estadosSelecionados];
    updatedEstados[index] = value;
    setEstadosSelecionados(updatedEstados);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-purple-100 to-purple-300">
      <div className="w-full max-w-md bg-white p-10 rounded-2xl shadow-lg border border-gray-200">
        <h1 className="text-4xl font-semibold text-center text-purple-800 mb-6">Crie sua Conta</h1>
        <p className="text-center text-gray-600 mb-8">Preencha os dados abaixo para se cadastrar.</p>

        <form onSubmit={registerUser} className="space-y-6">
          <div className="flex flex-col space-y-4">
            <input
              type="text"
              placeholder="Nome"
              value={nome}
              onChange={(ev) => setNome(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="email"
              placeholder="Seu email"
              value={email}
              onChange={(ev) => setEmail(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="password"
              placeholder="Senha"
              value={senha}
              onChange={(ev) => setSenha(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="password"
              placeholder="Confirme a senha"
              value={senhaConfirma}
              onChange={(ev) => setSenhaConfirma(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="text"
              placeholder="CNPJ"
              pattern="\d*"
              maxLength={14}
              value={cnpj}
              onChange={(ev) => setCnpj(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="text"
              placeholder="Telefone"
              pattern="\d*"
              maxLength={11}
              value={telefone}
              onChange={(ev) => setTelefone(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="text"
              placeholder="Endereço"
              value={endereco}
              onChange={(ev) => setEndereco(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
            type="number"
            placeholder="Quantidade de estados com cobertura"
            value={numEstados}
            onChange={(ev) => setNumEstados(ev.target.value)}
            className="mt-4 p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200" 
            min={1}
            max={27}
            required
            
          />

            <input
            type="text"
            placeholder="Descrição (Opcional)"
            value={descricao}
            onChange={(ev) => setDescricao(ev.target.value)}
            className="mt-4 p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200" 
            required
            
          />
          </div>


          <div id="estadosContainer">
            {Array.from({ length: numEstados }).map((_, index) => (
              <select
                key={index}
                value={estadosSelecionados[index]}
                onChange={(e) => handleEstadoChange(index, e.target.value)}
                className="p-4 border border-gray-300 rounded-xl mt-4"
                required
              >
                <option value="">Selecione um estado</option>
                {estados.map((estado) => (
                  <option key={estado} value={estado}>
                    {estado}
                  </option>
                ))}
              </select>
            ))}
          </div>


          <button
            type="submit"
            className="w-full bg-purple-600 text-white py-3 rounded-xl text-lg hover:bg-purple-700 focus:outline-none transition duration-300"
          >
            Registrar
          </button>

          <div className="text-center text-gray-500 py-4">
            Já tem uma conta? 
            <Link to="/login" className="text-purple-600 font-semibold hover:underline">
              Faça Login
            </Link>
          </div>
        </form>

        <script>

        </script>

      </div>
    </div>
  );
}
