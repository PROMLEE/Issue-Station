import { Link } from "react-router-dom";

export const ProjectCard = ({ project }) => {
  return (
    <Link
      className="border-2 border-sky-400 m-3 p-2 rounded-md block hover:bg-sky-100"
      to={`/project/${project.id}`}
    >
      <div className="text-3xl text-sky-500 font-bold mb-2">
        📰 {project.name}
      </div>
      <div className="text-2xl text-sky-800 font-bold mb-1">
        {project.description}
      </div>
      <div>{project.public ? "public" : "private"}</div>
      <div>Admin: {project.admin}</div>
      <div>Create Date: {project.created_at}</div>
    </Link>
  );
};
