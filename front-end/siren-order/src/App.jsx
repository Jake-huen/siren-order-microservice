import "./App.css"
import Navigation from "./components/Navigation";
import Main from "./components/Main";
import {BrowserRouter} from 'react-router-dom';

const App = () => {

    return (
        <>
        <BrowserRouter>
        <Navigation />
        </BrowserRouter>
            
        </>
    );  
};

export default App;