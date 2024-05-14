import { NavComponent } from "./Components/Navbar";
import { Component } from "./Components/Footer";
import { Route, Routes } from "react-router-dom";
import { Home } from "./Components/Home";
import { LoginComponent } from "./Components/Login";
import { SignupComponent } from "./Components/Signup";
import { ProjectList } from "./Components/ProjectList";
import { Team } from "./Components/Team";
import { AboutUs } from "./Components/AboutUs";

function App() {
  return (
    <div>
      <NavComponent />
      <div className="w-[80%] mx-auto py-10">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginComponent />} />
          <Route path="/signup" element={<SignupComponent />} />
          <Route path="/projectlist" element={<ProjectList />} />
          <Route path="/team" element={<Team />} />
          <Route path="/aboutus" element={<AboutUs />} />
        </Routes>
      </div>
      <Component />
    </div>
  );
}

export default App;
