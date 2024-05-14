import data from "../assets/main.json";

export const Home = () => {
  return (
    <div className="w-full flex flex-col">
      <div className="text-4xl text-sky-600 font-bold">
        이슈 관리 프로그램{" "}
        <div className="text-sky-800 inline underline decoration-wavy underline-offset-4">
          이슈역
        </div>
        에 온 여러분을 환영합니다!
      </div>
      {data.features.map((feature) => (
        <div key={feature.id} className="mt-3">
          <div className="my-2">
            <div className="inline my-3 underline decoration-double underline-offset-4">
              🚅
            </div>
            <div className="font-bold text-lg inline"> {feature.name}</div>
          </div>
          <div className="indent-2">{feature.description}</div>
        </div>
      ))}
    </div>
  );
};
