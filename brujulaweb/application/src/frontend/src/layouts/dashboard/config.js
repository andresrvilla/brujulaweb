import ChartBarIcon from '@heroicons/react/24/solid/ChartBarIcon';
import CogIcon from '@heroicons/react/24/solid/CogIcon';
import PowerIcon from '@heroicons/react/24/solid/PowerIcon';
import UsersIcon from '@heroicons/react/24/solid/UsersIcon';
import MapPinIcon from '@heroicons/react/24/solid/MapPinIcon'
import EnvelopeIcon from '@heroicons/react/24/solid/EnvelopeIcon'
import DocumentIcon from '@heroicons/react/24/solid/DocumentIcon'
import { SvgIcon } from '@mui/material';

export const items = [
  {
    title: 'Dashboard',
    path: '/',
    icon: (
      <SvgIcon fontSize="small">
        <ChartBarIcon />
      </SvgIcon>
    )
  },
  {
    title: 'Personas',
    path: '/customers',
    icon: (
      <SvgIcon fontSize="small">
        <UsersIcon />
      </SvgIcon>
    )
  },
  {
    title: 'Eventos',
    path: '/companies',
    icon: (
      <SvgIcon fontSize="small">
        <MapPinIcon />
      </SvgIcon>
    )
  },
  {
    title: 'Mensajes',
    path: '/account',
    icon: (
      <SvgIcon fontSize="small">
        <EnvelopeIcon />
      </SvgIcon>
    )
  },
  {
    title: 'Configuración',
    path: '/settings',
    icon: (
      <SvgIcon fontSize="small">
        <CogIcon />
      </SvgIcon>
    )
  },
  {
    title: 'Archivos generados',
    path: '/settings',
    icon: (
      <SvgIcon fontSize="small">
        <DocumentIcon />
      </SvgIcon>
    )
  },
  {
    title: 'Salir',
    path: '/auth/login',
    icon: (
      <SvgIcon fontSize="small">
        <PowerIcon />
      </SvgIcon>
    )
  }
];
