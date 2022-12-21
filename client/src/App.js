import './App.css';
import { BrowserRouter } from 'react-router-dom';
import Header from './Components/Header';
import Signup from './Pages/Signup/Signup';

function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Header/>
        <Signup />
      </div>
    </BrowserRouter>
  );
}

export default App;