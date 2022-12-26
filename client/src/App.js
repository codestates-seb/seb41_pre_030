import './App.css';
import Sidebar from "./Components/Sidebar";
import { Route, useLocation, Routes } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';
import LoginPage from './LoginPages/LoginPage';
import Signup from './Pages/Signup/Signup';
import UserPage from './Pages/UserPage/UserPage';

function App() {
  const [question] = useFetch('http://localhost:3001/questions/')
  const excludedRoutes = ['/signup', '/login'];
  const location = useLocation();
  return (
    <div className="App">
        <Header/>
        {!excludedRoutes.includes(location.pathname) && <Sidebar />}
        <Routes>
          <Route path='/member/*' element={<UserPage />}/>
          <Route path='/signup' element={<Signup />}/>
          <Route path='/users' element={<Users />}/>
          <Route path='/login' element={<LoginPage />} />
          <Route exact path='/' element={<Home questions={question}/>}/>
        </Routes>
        {!excludedRoutes.includes(location.pathname) && <Footer />}
    </div>
  );
}

  export default App;
