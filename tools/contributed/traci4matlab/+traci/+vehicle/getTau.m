function tau = getTau(vehID)
%getTau Returns the driver's reaction time in s for this vehicle.
%   tau = getTau(VEHID) Returns the driver's reaction time in s for this 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
tau = traci.vehicle.getUniversal(constants.VAR_TAU, vehID);