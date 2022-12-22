import './App.css';
import Sidebar from "./Components/Sidebar";
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';
import Header from './Components/Header';
import Footer from './Components/Footer';
import useFetch from './Components/util/useFetch';

function App() {
  const [question] = useFetch('http://localhost:3001/questions/')
  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Sidebar/>
          <Routes>
            <Route path='/' element={<Home questions={question}/>}/>
            <Route path='/users' element={<Users />}/>
          </Routes>
          <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;