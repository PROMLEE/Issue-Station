import React from "react";
import { Bar } from "react-chartjs-2";
import "chart.js/auto";
import dayjs from "dayjs";

const IssueTrendChart = ({ data }) => {
  const { dailyData, statusData } = processData(data);

  const dailyLabels = Object.keys(dailyData);
  const dailyCounts = Object.values(dailyData);

  const statusLabels = [
    "NEW",
    "ASSIGNED",
    "FIXED",
    "RESOLVED",
    "CLOSED",
    "REOPENED",
  ];
  const statusColors = [
    "rgba(255, 99, 132, 0.6)", // NEW
    "rgba(54, 162, 235, 0.6)", // ASSIGNED
    "rgba(255, 206, 86, 0.6)", // FIXED
    "rgba(75, 192, 192, 0.6)", // RESOLVED
    "rgba(153, 102, 255, 0.6)", // CLOSED
    "rgba(255, 159, 64, 0.6)", // REOPENED
  ];
  const statusCounts = statusLabels.map((label) => statusData[label] || 0);
  const dailyChartData = {
    labels: dailyLabels,
    datasets: [
      {
        label: "Daily Issues",
        data: dailyCounts,
        backgroundColor: "rgba(75, 192, 192, 0.6)",
      },
    ],
  };
  const statusChartData = {
    labels: statusLabels,
    datasets: [
      {
        label: "Issues by Status",
        data: statusCounts,
        backgroundColor: statusColors,
      },
    ],
  };

  const options = {
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          stepSize: 1,
          precision: 0,
        },
      },
    },
  };
  return (
    <div className=" p-5 border">
      <h2 className="text-xl font-bold mb-4">Issue Trends</h2>
      <h3 className="text-lg font-semibold mb-2">Daily Issue Trend</h3>
      <Bar data={dailyChartData} options={options} />
      <h3 className="text-lg font-semibold mb-2">Issues by Status</h3>
      <Bar data={statusChartData} options={options} />
    </div>
  );
};

const processData = (data) => {
  const dailyData = {};
  const statusData = {};

  data.forEach((issue) => {
    const day = dayjs(issue.initDate).format("YYYY-MM-DD");
    const status = issue.status;

    if (dailyData[day]) {
      dailyData[day]++;
    } else {
      dailyData[day] = 1;
    }

    if (statusData[status]) {
      statusData[status]++;
    } else {
      statusData[status] = 1;
    }
  });

  return { dailyData, statusData };
};

export default IssueTrendChart;
