import { NavComponent } from "./Layout/Navbar";
import { Component } from "./Layout/Footer";
import { Link, Route, Routes } from "react-router-dom";
import { Home } from "./Page/Home";
import { LoginComponent } from "./Page/Login";
import { SignupComponent } from "./Page/Signup";
import { ProjectList } from "./Page/ProjectList";
import { CreateProject } from "./Page/CreateProject";
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
          <Route path="/myproject" element={<ProjectList my={true} />} />
          <Route path="/createproject" element={<CreateProject />} />
          <Route path="/aboutus" element={<AboutUs />} />
          <Route path="/project/:id" element={<Project />} />
          <Route path="/issue/:id" element={<Issue />} />
          <Route
            path="*"
            element={
              <>
                <div className="text-4xl text-red-500">📛404 Not Found</div>
                <br />
                <Link to="/" className="text-2xl text-blue-500 underline">
                  Home
                </Link>
              </>
            }
          />
        </Routes>
      </div>
      <Component />
    </div>
  );
}

export default App;
