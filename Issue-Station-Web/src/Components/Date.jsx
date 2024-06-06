import { Badge } from "flowbite-react";
import { HiClock } from "react-icons/hi";

export function Date({ date, style, create = true }) {
  return (
    <div className="flex gap-2 items-center">
      <div className={style}>{create ? "Created" : "Modified"} at: </div>
      <Badge color="gray" icon={HiClock} className="px-2">
        {("" + date).substr(0, 10)} {("" + date).substr(11, 8)}
      </Badge>
    </div>
  );
}
