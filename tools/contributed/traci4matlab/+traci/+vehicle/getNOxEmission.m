function NOxEmission = getNOxEmission(vehID)
%getNOxEmission Get the NOx emission of the vehicle.
%   NOxEmission = getNOxEmission(VEHID) Returns the NOx emission in mg for
%   the last time step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNOxEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
NOxEmission = traci.vehicle.getUniversal(constants.VAR_NOXEMISSION, vehID);