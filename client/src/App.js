import './App.css';
import Sidebar from "./Components/Sidebar";
import { Routes, Route, useLocation } from 'react-router-dom';
import Home from './Pages/Home/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';
<<<<<<< HEAD
import LoginPage from './Pages/Login/LoginForm';
import Signup from './Pages/Signup/Signup';
import AskPage from './Pages/AskPages/AskForm';
=======
import LoginPage from './Pages/LoginPages/LoginPage';
import Signup from './Pages/Signup/Signup';
import UserPage from './Pages/UserPage/UserPage';
>>>>>>> 246c7071dab613a0d352b0816102fb87ee38321d

function App() {
  const [question] = useFetch('http://localhost:3001/questions/')
  const excludedRoutes = ['/signup', '/login', '/ask'];
  const location = useLocation();
  return (
    <div className="App">
        <Header/>
        {!excludedRoutes.includes(location.pathname) && <Sidebar />}
        <Routes>
          <Route path='/member/*' element={<UserPage />}/>
          <Route path='/signup' element={<Signup />}/>
          <Route path='/members' element={<Users />}/>
          <Route path='/login' element={<LoginPage />} />
<<<<<<< HEAD
          <Route path='/ask' element={<AskPage />}/>
          <Route path='/' element={<Home questions={question}/>}/>
=======
          <Route exact path='/' element={<Home questions={question}/>}/>
>>>>>>> 246c7071dab613a0d352b0816102fb87ee38321d
        </Routes>
        {!excludedRoutes.includes(location.pathname) && <Footer />}
    </div>
  );
}

export default App;