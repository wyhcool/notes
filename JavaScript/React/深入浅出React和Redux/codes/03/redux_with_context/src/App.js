import './App.css';
import Provider from './Provider';
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
