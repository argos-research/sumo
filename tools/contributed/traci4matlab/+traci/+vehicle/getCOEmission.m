function COEmission = getCOEmission(vehID)
%getCOEmission Returns the CO emission of the vehicle.
%   COEmission = getCO2Emission(VEHID) Returns the CO emission in mg for
%   the last time step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getCOEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
COEmission = traci.vehicle.getUniversal(constants.VAR_COEMISSION, vehID);