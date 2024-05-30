import { useEffect, useState } from "react";
import { ProjectCard } from "../Components/ProjectCard";
import { SearchProject, MyProject } from "../apis/project";
import { useLocation } from "react-router-dom";
import isLogin from "../util/checklogin";
import { useNavigate } from "react-router-dom";
export const ProjectList = ({ my = false }) => {
  const [projectlist, setProjectlist] = useState([]);
  const { state } = useLocation();
  const nav = useNavigate();
  useEffect(() => {
    if (my) {
      if (!isLogin()) {
        alert("로그인이 필요합니다.");
        nav("/login");
        return;
      }
      getMyProject();
    } else {
      getProjectList();
    }
  }, [state, my]);

  const getProjectList = async () => {
    const response = await SearchProject(state === null ? "" : state.search);
    setProjectlist(response.data);
  };

  const getMyProject = async () => {
    try {
      const response = await MyProject();
      setProjectlist(response.data);
    } catch (e) {
      console.log(e);
    }
  };
  return (
    <div className="flex flex-wrap">
      {projectlist.length ? (
        projectlist.map((project) => {
          return <ProjectCard key={project.id} project={project} />;
        })
      ) : (
        <div>프로젝트가 존재하지 않습니다.</div>
      )}
    </div>
  );
};
