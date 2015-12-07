clear all
close all
clc

% Define the vehicle types
type_taxi = sumolib.demand.VehicleType('taxi','vClass', 'taxi');
type_carro = sumolib.demand.VehicleType('carro', 'color', [1 0 0],...
    'vClass', 'passenger', 'guiShape', 'passenger');
type_bus = sumolib.demand.VehicleType('bus', 'color', [0 1 0],...
    'vClass', 'bus', 'guiShape', 'bus');

busStopE = sumolib.demand.BusStop('sanDiegoE', '-465_1', 'line', '--');
busStopW = sumolib.demand.BusStop('sanDiegoW', '-447_1', 'line', '--');

stopE = sumolib.demand.Stop({busStopE},'duration',120);
stopW = sumolib.demand.Stop({busStopW},'duration',120);


% Generate demands for some edges
flow1 = sumolib.demand.Flow('33463249#0', 'type', type_taxi, 'demandType',...
    'vehsPerHour', 'demand', 800);
flow2 = sumolib.demand.Flow('-380', 'type', type_taxi, 'demandType',...
    'vehsPerHour', 'demand', 800);
flow3 = sumolib.demand.Flow('30659373#0', 'type', type_carro, 'demandType',...
    'vehsPerHour', 'demand', 500);
flow4 = sumolib.demand.Flow('33463249#0', 'type', type_carro, 'demandType',...
    'vehsPerHour', 'demand', 1500);
flow5 = sumolib.demand.Flow('-380', 'type', type_carro, 'demandType',...
    'vehsPerHour', 'demand', 1500);
flow6 = sumolib.demand.Flow('33463249#0', 'to', '-461#1', 'type', type_bus,...
    'demandType', 'period', 'demand', 450, 'stop', {stopW});
flow7 = sumolib.demand.Flow('-380', 'to', '-436#1', 'type', type_bus,...
    'demandType', 'period', 'demand', 450, 'stop', {stopE});


% Define turning probabilities
turns_flow1 = sumolib.demand.TurnProbability('33463249#0', {'30659403' '33463249#1'},...
    {0.2 0.8});
turns_flow2 = sumolib.demand.TurnProbability('-380', {'-465'},{1});
turns_flow3 = sumolib.demand.TurnProbability('30659373#0', {'30659375' '30659373#1'},...
    {0.6 0.4});


% flow1_1 = sumolib.demand.Flow('O1a',0,...
%     round(120*rand(1,30)),containers.Map({'aO4'},{1}));
% 
% flow1_2 = sumolib.demand.Flow('O1a',0,...
%     round(120*rand(1,30)),containers.Map({'aO3'},{1}));

% Create an array of demands
flows = {flow1 flow2 flow3 flow4 flow5};
turns = {turns_flow1 turns_flow2 turns_flow1 turns_flow2 turns_flow3};

% Get the filename of the example scenario
[scenarioPath,~,~] = fileparts(which(mfilename));
% scenarioPath = [scenarioPath '\inter_palmas'];
cd(scenarioPath);

% Generate demand for vehicles, using turns
sumolib.demand.generateDemand('inter_palmas_notls.net.xml',...
	flows, 50400, 54000, turns);

% Generate demand for public transport, using origin and destination edges
busFlows = {flow6 flow7};
sumolib.demand.generateDemand('inter_palmas_notls.net.xml',...
	busFlows, 50400, 54000, 'prefix', 'inter_palmas_buses');

