import { NavComponent } from "./Components/Navbar";
import { Component } from "./Components/Footer";
import { Route, Routes } from "react-router-dom";
import { Home } from "./Components/Home";
import { Login } from "./Components/Login";
import { Signup } from "./Components/Signup";
import { ProjectList } from "./Components/ProjectList";
import { Team } from "./Components/Team";
import { AboutUs } from "./Components/AboutUs";

function App() {
  return (
    <div>
      <NavComponent />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/projectlist" element={<ProjectList />} />
        <Route path="/team" element={<Team />} />
        <Route path="/aboutus" element={<AboutUs />} />
      </Routes>
      <Component />
    </div>
  );
}

export default App;
