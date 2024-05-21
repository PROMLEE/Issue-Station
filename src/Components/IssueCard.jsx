import { Tag } from "./Tag";
import { Date } from "./Date";
import { Button, Card } from "flowbite-react";
import { Link } from "react-router-dom";

export const IssueCard = ({ issue }) => {
  return (
    <Card className="min-w-[15rem] m-5 flex-grow">
      <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
        {issue.name}
      </h5>
      <div className="font-normal text-gray-700 dark:text-gray-400">
        {issue.description}
      </div>
      <div className="flex gap-2 my-1">
        <div>status: </div>
        <Tag status={issue.status} />
      </div>
      <div className="">reporter: {issue.reporter}</div>
      <div className="">assignee: {issue.assignee}</div>
      <Date date={issue.initdate} />
      <Link to={`/issue/${issue.id}`} className="w-full">
        <Button className="w-full">
          Read more
          <svg
            className="-mr-1 ml-2 h-4 w-4"
            fill="currentColor"
            viewBox="0 0 20 20"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fillRule="evenodd"
              d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
              clipRule="evenodd"
            />
          </svg>
        </Button>
      </Link>
    </Card>
  );
};
