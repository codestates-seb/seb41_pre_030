import './App.css';
import Sidebar from "./Components/Sidebar";
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import LoginPage from './LoginPages/LoginPage';


function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Sidebar/>
          <Routes>
            <Route path='/' element={<Home/>}/>
            <Route path='/users' element={<Users />}/>
            <Route path='/login' element={<LoginPage />} />
          </Routes>
          <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;