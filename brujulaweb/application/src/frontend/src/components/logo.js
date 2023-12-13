import { useTheme } from '@mui/material/styles';
import {
  Box
} from '@mui/material';

export const Logo = () => {
  const theme = useTheme();
  const fillColor = theme.palette.primary.main;

  return (
    <Box>
      <img src='/assets/logos/32.png' />
      <title>BrujulaWEB</title>
    </Box>
    
  );
};
