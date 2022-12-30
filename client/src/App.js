import './App.css';
import Sidebar from "./Components/Sidebar";
import { Routes, Route, useLocation } from 'react-router-dom';
import Home from './Pages/Home/Home';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';
import AskPage from './Pages/AskPages/AskForm';
import LoginPage from './Pages/LoginPages/LoginPage';
import Signup from './Pages/Signup/Signup';
import QuestionDetail from './Pages/Question/QuestionDetail';
import UserPage from './Pages/UserPage/UserPage';
import Search from './Pages/Search/Search';
import AllUsers from './Pages/UserlistPage/AllUsers';
import QuestionEditForm from './Pages/AskPages/QuestionEditForm';

function App() {
  const [question] = useFetch('http://13.125.30.88:8080/questions/')
  const location = useLocation();
  
  return (
    <div className="App">
        <Header/>
        {!['/signup', '/login', '/ask', '/askEdit'].includes(location.pathname.slice(0,8)) && <Sidebar />}
        <Routes>
          <Route path='/askEdit/:id' element={<QuestionEditForm />} />
          <Route path='/search' element={<Search />} />
          <Route path='/questions/:id' element={<QuestionDetail />}/>
          <Route path='/member/*' element={<UserPage />}/>
          <Route path='/signup' element={<Signup />}/>
          <Route path='/login' element={<LoginPage />} />
          <Route path='/ask' element={<AskPage />}/>
          <Route path='/members' element={<AllUsers />}/>
          <Route exact path='/' element={<Home questions={question}/>}/>
        </Routes>
        {!['/signup', '/login'].includes(location.pathname) && <Footer />}
    </div>
  );
}

export default App;