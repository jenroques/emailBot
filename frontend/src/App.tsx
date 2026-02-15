import { Box, Card, CardContent, Container, Grid, Stack, Typography } from '@mui/material';

const services = [
  { name: 'Conversations Indexed', value: '2.1M' },
  { name: 'Active Triage Rules', value: '84' },
  { name: 'Model Inference Latency', value: '84ms' },
  { name: 'Escalations Today', value: '17' },
];

export function App() {
  return (
    <Container maxWidth="lg" sx={{ py: 6 }}>
      <Stack spacing={3}>
        <Box>
          <Typography variant="h4" fontWeight={700}>
            Enterprise Inbox Intelligence Platform
          </Typography>
          <Typography variant="body1" color="text.secondary">
            Operational dashboard starter for inbox automation, triage, and analytics.
          </Typography>
        </Box>

        <Grid container spacing={2}>
          {services.map((item) => (
            <Grid key={item.name} item xs={12} sm={6} md={3}>
              <Card>
                <CardContent>
                  <Typography variant="body2" color="text.secondary">
                    {item.name}
                  </Typography>
                  <Typography variant="h5" fontWeight={700}>
                    {item.value}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Stack>
    </Container>
  );
}
