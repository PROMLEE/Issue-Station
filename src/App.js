import { NavComponent } from "./Layout/Navbar";
import { Component } from "./Layout/Footer";
import { Route, Routes } from "react-router-dom";
import { Home } from "./Page/Home";
import { LoginComponent } from "./Page/Login";
import { SignupComponent } from "./Page/Signup";
import { ProjectList } from "./Page/ProjectList";
import { Team } from "./Page/Team";
import { AboutUs } from "./Page/AboutUs";
import { Project } from "./Page/Project";
import { Issue } from "./Page/Issue";

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
          <Route path="/project/:id" element={<Project />} />
          <Route path="/issue/:id" element={<Issue />} />
        </Routes>
      </div>
      <Component />
    </div>
  );
}

export default App;
