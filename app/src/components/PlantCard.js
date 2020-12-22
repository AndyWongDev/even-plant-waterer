import { Card, Avatar } from 'antd';
import { BgColorsOutlined, EditOutlined } from '@ant-design/icons';
import ScheduleButton from './ScheduleButton';
import MyResponsiveLine from './Chart';
import React, { useEffect } from 'react';

const { Meta } = Card;

const PlantCard = (props) => {
  const dataPoints = [];

  useEffect(() => {
    const date = new Date().toISOString();
    const query = `/schedules?pinId=${props.pinId}&startTime=${date}&duration=10`;
    fetch(query)
      .then((res) => res.json())
      .then((res) =>
        res.map((timeAndVolume) => ({
          x: timeAndVolume.time,
          y: timeAndVolume.volume || 1,
        })),
      )
      .then((res) => dataPoints.push(...res));
  }, []);

  const data = [
    {
      id: 'WateringTimes',
      color: 'hsl(98, 70%, 50%)',
      data: dataPoints,
    },
  ];

  return (
    <Card
      style={{ width: 500, margin: 16, display: 'inline-block' }}
      cover={
        <img
          alt="cactus"
          src="https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/cactus-plant-pot-stand-1599155636.jpg"
        />
      }
      actions={[
        <ScheduleButton />,
        <EditOutlined key="edit" />,
        <BgColorsOutlined key="water" disabled={true} />,
      ]}
    >
      <Meta
        avatar={
          <Avatar src="https://avatars0.githubusercontent.com/u/4334491?s=460&v=4" />
        }
        title="One Very Happy Plant"
        description={
          <>
            <p>Plant ID: {props.id}</p>
            <p>Pin ID: {props.pinId}</p>
            <p>Plant Type: {props.plantType}</p>
            <p>Volume: {props.volume}</p>
            <p>Schedule: {props.schedule}</p>
          </>
        }
      />
      <div className="LineGraph" style={{ height: 200 }}>
        {MyResponsiveLine({ data })}
      </div>
    </Card>
  );
};

export default PlantCard;
