import './App.css';
import Sidebar from "./Components/Sidebar";
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import Home from './Components/Home';
import Users from './Components/Users';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        {<Sidebar/>}
          <Routes>
            <Route path='/' element={<Home/>}/>
            <Route path='/users' element={<Users />}/>
          </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;