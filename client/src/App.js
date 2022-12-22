import './App.css';
import Sidebar from "./Components/Sidebar";
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import Signup from './Pages/Signup/Signup';

function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Sidebar/>
          <Routes>
            <Route path='/signup' element={<Signup />}/>
            <Route path='/users' element={<Users />}/>
            <Route path='/' element={<Home/>}/>
          </Routes>
        <Footer/>
      </div>
    </BrowserRouter>
  );
}

  export default App;
