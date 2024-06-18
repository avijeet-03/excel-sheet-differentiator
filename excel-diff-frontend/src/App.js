import './App.css';
import FileUpload from './components/FileUpload';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Excel File Difference Checker</h1>
        <FileUpload/>
      </header>
    </div> 
  );
}

export default App;
