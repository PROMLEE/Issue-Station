import projectlist from "../assets/projectlist_dump.json";
import { ProjectCard } from "../Components/ProjectCard";

export const ProjectList = () => {
  return (
    <div className="flex flex-wrap">
      {projectlist.projectlist.map((project) => {
        return <ProjectCard key={project.projectid} project={project} />;
      })}
    </div>
  );
};
