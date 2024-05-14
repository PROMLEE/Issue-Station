import members from "../assets/aboutus.json";

export const AboutUs = () => {
  return (
    <div>
      <div className="text-3xl text-sky-500 font-bold">
        우리는 이슈역이에요!
      </div>
      {members.members.map((member, index) => {
        return (
          <div key={index}>
            <div>이름: {member.name}</div>
            <div>age: {member.age}</div>
            <div>email: {member.email}</div>
          </div>
        );
      })}
    </div>
  );
};
