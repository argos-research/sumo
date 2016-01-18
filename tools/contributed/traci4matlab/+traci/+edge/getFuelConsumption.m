function fuelConsumption = getFuelConsumption(edgeID)
%getFuelConsumption Get the fuel consumption on the edge.
%   fuelConsumption = getFuelConsumption(EDGEID) Returns the fuel 
%   consumption in ml for the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getFuelConsumption.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
fuelConsumption = traci.edge.getUniversal(constants.VAR_FUELCONSUMPTION, edgeID);