import projectlist from "../assets/projectlist_dump.json";
import { ProjectCard } from "../Components/ProjectCard";

export const ProjectList = () => {
  return (
    <div>
      {projectlist.projectlist.map((project) => {
        return <ProjectCard key={project.id} project={project} />;
      })}
    </div>
  );
};
