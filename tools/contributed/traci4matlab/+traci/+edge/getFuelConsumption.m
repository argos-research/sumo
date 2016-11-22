function fuelConsumption = getFuelConsumption(edgeID)
%getFuelConsumption Get the fuel consumption on the edge.
%   fuelConsumption = getFuelConsumption(EDGEID) Returns the fuel 
%   consumption in ml for the last time step on the given edge.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getFuelConsumption.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
fuelConsumption = traci.edge.getUniversal(constants.VAR_FUELCONSUMPTION, edgeID);