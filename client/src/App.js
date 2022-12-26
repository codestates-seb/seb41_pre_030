import './App.css';
import Sidebar from "./Components/Sidebar";
import { Routes, Route, useLocation } from 'react-router-dom';
import Home from './Pages/Home/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';
import LoginPage from './Pages/LoginPages/LoginPage';
import Signup from './Pages/Signup/Signup';
import QuestionDetail from './Pages/Question/QuestionDetail';
import UserPage from './Pages/UserPage/UserPage';

function App() {
  const [question] = useFetch('http://localhost:3001/questions/')
  // const excludedRoutes = ['/signup', '/login'];
  const location = useLocation();
  return (
    <div className="App">
        <Header/>
        {!['/signup', '/login', '/ask'].includes(location.pathname) && <Sidebar />}
        <Routes>
          <Route path='/questions/:id' element={<QuestionDetail />}/>
          <Route path='/member/*' element={<UserPage />}/>
          <Route path='/signup' element={<Signup />}/>
          <Route path='/members' element={<Users />}/>
          <Route path='/login' element={<LoginPage />} />
          <Route exact path='/' element={<Home questions={question}/>}/>
        </Routes>
        {!['/signup', '/login'].includes(location.pathname) && <Footer />}
    </div>
  );
}

export default App;