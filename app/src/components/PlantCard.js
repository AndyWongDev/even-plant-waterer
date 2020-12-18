import { Card, Avatar } from 'antd';
import { BgColorsOutlined, EditOutlined } from '@ant-design/icons';
import ScheduleButton from './ScheduleButton';

const { Meta } = Card;

const PlantCard = (props) => (
  <Card
    style={{ width: 300, margin: 16, display: 'inline-block' }}
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
  </Card>
);

export default PlantCard;
