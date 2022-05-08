import { Provider } from 'react-redux';
import './App.css';
import store from './Store';
import { ControlPanel } from './views/ControlPanel';

function App() {
  return (
    <Provider store={store}>
      <ControlPanel></ControlPanel>
    </Provider>
  );
}

export default App;
