import React from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import PrivateRoute from './components/PrivateRoute';
import LoginPage from './pages/LoginPage';
import DoctorsPage from './pages/DoctorsPage';

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <div className="app">
          <Switch>
            <Route exact path="/login" component={LoginPage} />
            <PrivateRoute exact path="/doctors" component={DoctorsPage} />
            <Route exact path="/">
              <Redirect to="/doctors" />
            </Route>
          </Switch>
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;

