import './App.css';
import Sidebar from "./Components/Sidebar";
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';
import LoginPage from './LoginPages/LoginPage';
import Signup from './Pages/Signup/Signup';

function App() {
  const [question] = useFetch('http://localhost:3001/questions/')
  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Sidebar/>
          <Routes>
            <Route path='/signup' element={<Signup />}/>
            <Route path='/users' element={<Users />}/>
            <Route path='/login' element={<LoginPage />} />
            <Route path='/' element={<Home questions={question}/>}/>
          </Routes>
        <Footer/>
      </div>
    </BrowserRouter>
  );
}

  export default App;
