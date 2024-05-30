import members from "../assets/aboutus.json";

export const AboutUs = () => {
  return (
    <div>
      <div className="text-3xl text-sky-500 font-bold mb-2">
        우리는 이슈역이에요!
      </div>
      <div className="text-2xl text-sky-800 font-bold mb-1">이슈역 승무원</div>
      {members.members.map((member, index) => {
        return (
          <div key={index} className="mt-3 flex w-full">
            <a
              href={member.github}
              target="_blank"
              rel="noreferrer"
              className="w-1/5 border-2 border-gray-200"
            >
              <img
                src={member.image}
                className="w-[10rem] m-4 rounded-lg mx-auto"
                alt="dk"
              />
            </a>
            <div className="border-2 border-gray-200 p-4 w-4/5 flex content-center flex-col">
              <div className="font-bold">Name: {member.name}</div>
              <div>Part: {member.part}</div>
              <div>Age: {member.age}</div>
              <div>E-mail: {member.email}</div>
              <div>
                Github:{" "}
                <a href={member.github} className="text-blue-500">
                  {member.github}
                </a>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};
