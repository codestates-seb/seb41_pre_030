import './App.css';
import Sidebar from "./Components/Sidebar";
import { Routes, Route, useLocation } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';
import LoginPage from './LoginPages/LoginPage';
import Signup from './Pages/Signup/Signup';

function App() {
  const [question] = useFetch('http://localhost:3001/questions/')
  const excludedRoutes = ['/signup', '/login'];
  const location = useLocation();
  return (
    <div className="App">
        <Header/>
        {!excludedRoutes.includes(location.pathname) && <Sidebar />}
        <Routes>
          <Route path='/signup' element={<Signup />}/>
          <Route path='/members' element={<Users />}/>
          <Route path='/login' element={<LoginPage />} />
          <Route path='/' element={<Home questions={question}/>}/>
        </Routes>
        {!excludedRoutes.includes(location.pathname) && <Footer />}
    </div>
  );
}

export default App;